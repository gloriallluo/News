import requests
import json
import numpy
from sklearn.cluster import KMeans
from sklearn.manifold import TSNE
from sklearn.feature_extraction.text import TfidfVectorizer

corpus = []
elist = []

class MyEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, numpy.integer):
            return int(obj)
        elif isinstance(obj, numpy.floating):
            return float(obj)
        elif isinstance(obj, numpy.ndarray):
            return obj.tolist()
        else:
            return super(MyEncoder, self).default(obj)

def get_response_list():
    """
    Send a request and get the json response
    """
    url = 'https://covid-dashboard.aminer.cn/api/events/list?type=event&page=%d&size=%d'
    for i in range(1, 40):
        try:
            response = requests.get(url % (i, 20))
            response.encoding = 'utf-8'
        except:
            break
        response = dict(response.json())
        response = response['data']
        for event in response:
            elist.append({
                'id': event['id'],
                'date': event['date'],
                'seg_text': event['seg_text'],
                'title': event['title']
            })
            corpus.append(event['seg_text'])
    return elist


def get_vector():
    vectorizer = TfidfVectorizer()
    X = vectorizer.fit_transform(corpus)
    X = X.toarray()
    tsne = TSNE(n_components=3)
    X = tsne.fit_transform(X)
    return X


def get_cluster(X):
    kmeans = KMeans(n_clusters=6, random_state=10).fit(X)
    return kmeans.labels_


def save_res(labels, elist):
    for i, event in enumerate(elist):
        elist[i]['label'] = labels[i]
    with open('cluster_result.json', 'w') as file:
        json.dump(elist, file, ensure_ascii=False, cls=MyEncoder)


if __name__ == '__main__':
    elist = get_response_list()
    X = get_vector()
    print(X.shape)
    labels = get_cluster(X)
    save_res(labels, elist)
    
    