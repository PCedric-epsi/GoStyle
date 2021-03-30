import json
import pymongo
from bson import json_util
from flask import Flask
from flask_restplus import Api, Resource
    

myclient = pymongo.MongoClient("mongodb+srv://admin:toor@mspr.mn5kl.mongodb.net/dbcodes?retryWrites=true&w=majority")
#myclient = pymongo.MongoClient("mongodb://localhost:27017/")
mydb = myclient["dbcodes"]

app = Flask(__name__)
api = Api(app=app, version='1.0', title='Qrcodes API', description='API de l\'application GoStyle', url='test', validate=True)

def parse_json(data):
    return json.loads(json_util.dumps(data))


@api.route("/api/codes")
class QrCodeList(Resource):
    def get(self):
        """
        Returns list of QrCode
        """

        cursor = mydb.qrcodes.find({}, {"_id": 0})
        data = []

        for code in cursor:
            data.append(code)
        
        # Fin méthode GET
        
        return data

@api.route("/api/code/<string:code>")
class Qrcode(Resource):
    def get(self, code):
        """
        Returns value of code if exist
        """
        cursor = mydb.qrcodes.find({"name": code}, {"_id": 0, "value": 1})
        test = mydb.qrcodes.find({"name": code}, {"_id": 0, "name": 1})

        json_parsed = test.next()['name']
        try:
            assert json_parsed == code
        except:
            return Flask.render_template('http://localhost:8887/404'), 404

        # Fin méthode GET

        return cursor[0]


if __name__ == '__main__':
    app.run(port=8887, host='localhost')
