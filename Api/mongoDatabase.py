import json

import pymongo
from bson import json_util, ObjectId
from flask import Flask
from flask_restplus import Api, Resource


myclient = pymongo.MongoClient("mongodb+srv://dbcodes:toor@mspr.mn5kl.mongodb.net/dbcodes?retryWrites=true&w=majority")
mydb = myclient["dbcodes"]

app = Flask(__name__)
api = Api(app=app, version='1.0', title='Qrcodes Api', description='Simple api', validate=True)


def parse_json(data):
    return json.loads(json_util.dumps(data))


@api.route("/api/")
class QrCodeList(Resource):

    def get(self):
        """
        returns list of QrCode
        """

        cursor = mydb.qrcodes.find({}, {"_id": 0})
        data = []

        for code in cursor:
            data.append(code)

        return data


@api.route("/api/<string:name>")
class Qrcode(Resource):

    def get(self, name):
        """
        returns value of code if exist
        """
        cursor = mydb.qrcodes.find({"name": name}, {"_id": 0, "value": 1})

        return cursor[0]


app.run(port=8887, host='localhost')
