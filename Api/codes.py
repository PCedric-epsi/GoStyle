import json

import pymongo
from bson import json_util, ObjectId
from flask import Flask
from flask_restplus import Api, Resource


'''myclient = pymongo.MongoClient("mongodb+srv://admin:toor@mspr.mn5kl.mongodb.net/dbcodes?retryWrites=true&w=majority")
mydb = myclient["dbcodes"]'''

myclient = pymongo.MongoClient("mongodb://localhost:27017/")
mydb = myclient["codes"]

app = Flask(__name__)
api = Api(app=app, version='1.0', title='Qrcodes API', description='API de l\'application GoStyle', url='test', validate=True)

@api.route("/api/codes")

class QrCodeList(Resource):

    @api.doc(responses={200: 'Ok'})
    def get(self):
        """
        Returns list of codes
        """

        cursor = mydb.qrcodes.find({}, {"_id": 0})
        data = []

        for code in cursor:
            data.append(code)

        return data

@api.route("/api/codes/<string:name>")
class Qrcode(Resource):

    def get(self, name):
        """
        Returns value of code if exist
        """
        cursor = mydb.qrcodes.find({"name": name}, {"_id": 0, "value": 1})

        if cursor.count() == 0:
            return {'message': 'No code found'}
        else: 
            return cursor.next()

app.run(port=8887, host='localhost')
