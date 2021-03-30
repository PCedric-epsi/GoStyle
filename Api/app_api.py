import json
import pymongo
from bson import json_util
from flask import Flask
from flask_restplus import Api, Resource
    
# Connexion à la base de donnée
myclient = pymongo.MongoClient("mongodb+srv://admin:toor@mspr.mn5kl.mongodb.net/dbcodes?retryWrites=true&w=majority")
#myclient = pymongo.MongoClient("mongodb://localhost:27017/")

# On utilise la collection dbcodes
mydb = myclient["dbcodes"]

app = Flask(__name__)
api = Api(app=app, version='1.0', title='Qrcodes API', description='API de l\'application GoStyle', url='test', validate=True)

def parse_json(data):
    return json.loads(json_util.dumps(data))

# Méthode GET qui retourne la liste de tous les codes et les values.
@api.route("/api/codes")
class QrCodeList(Resource):
    def get(self):
        """
        Returns list of codes and values
        """
        # requête NoSQL sur notre documment qrcodes
        cursor = mydb.qrcodes.find({}, {"_id": 0})
        data = []

        # ajout des codes dans un tableau
        for code in cursor:
            data.append(code)
        
        # Fin méthode GET
        return data

# Méthode GET qui retourne la value d'un code spécifique
@api.route("/api/code/<string:code>")
class Qrcode(Resource):
    def get(self, code):
        """
        Returns value of code if exist
        """
        # requête NoSQL sur notre documment qrcodes qui récupère uniquement la value du code
        cursor = mydb.qrcodes.find({"name": code}, {"_id": 0, "value": 1})

        # requête NoSQL sur notre documment qrcodes qui récupère uniquement le nom du code
        test = mydb.qrcodes.find({"name": code}, {"_id": 0, "name": 1})

        # conversion en String le nom du code
        json_parsed = test.next()['name']

        # on vérifie que la value qui est retourné est bien la value d'un code existant et aussi du bon code, si non on retourne une 404
        if (json_parsed == code):
            return cursor[0]
        else:
            return Flask.render_template('http://localhost:8887/404'), 404

        # Fin méthode GET


if __name__ == '__main__':
    app.run(port=8887, host='localhost')
