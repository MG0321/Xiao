import executor_utils as eu
import json
from flask import jsonify
from flask import request
from flask import Flask
app=Flask(__name__)

@app.route("/")
def hello():
    return "Hello World"

@app.route("/build_and_run",methods=["POST"])
def build_and_run():
    data=json.loads(request.data.decode('utf-8'))

    if 'code' not in data or 'lang' not in data:
        return "You should provide both 'code' and 'lang'"
    code=data['code']
    lang=data['lang']

    print("API got called with code: %s in %s" % (code, lang))
    result = eu.build_and_run(code, lang)
    return jsonify(result)



if __name__=="__main__":
    eu.load_images()
    app.run(debug=True)
