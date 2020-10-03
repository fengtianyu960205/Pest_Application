# search pest by name

import json
import pymysql

endpoint = "pestsdb.cukmz8althst.us-east-1.rds.amazonaws.com"
username = "admin"
password = "960205yft"
database_name = "PestInfoDB"
connection = pymysql.connect(endpoint, user=username, passwd=password, db=database_name)


def lambda_handler(event, context):
    CategoryName = event['queryStringParameters']['CategoryName']

    l = CategoryName.split(",")
    categroyNameList = ["rabbit", "fox", "dog", "cat", "fish", "mice", "bird", "pig", "toad", "deer", "ant", "plant"];

    if l[0] not in categroyNameList:
        cursor = connection.cursor()
        cursor.execute(
            'select * from Pest where pest_id in (select distinct pest_ID from Location where location_name = %s) ',
            (l[0]))
        rows = cursor.fetchall()
    else:
        if (len(l[0]) != 0 and l[1] == "State"):
            cursor = connection.cursor()
            cursor.execute('select * from Pest where pestName_category = %s ', (l[0]))
            rows = cursor.fetchall()
        elif (len(l[0]) != 0 and l[1] != "State"):
            cursor = connection.cursor()
            cursor.execute(
                'select * from Pest where pestName_category = %s and pest_id in (select distinct pest_ID from Location where State = %s) ',
                (l[0], l[1]))
            rows = cursor.fetchall()
    jsonArray = []
    for row in rows:
        jsonobj = {}
        jsonobj['pestId'] = row[0]
        jsonobj['pestName'] = row[1]
        jsonobj['weight'] = row[2]
        jsonobj['height'] = row[3]
        jsonobj['threat'] = row[4]
        jsonobj['country'] = row[5]
        jsonobj['category'] = row[6]
        jsonobj['diet'] = row[7]
        jsonobj['ways'] = row[8]
        jsonobj['tips'] = row[9]
        jsonobj['imageURL'] = row[10]
        jsonobj['score'] = row[11]

        jsonArray.append(jsonobj)

    finalResponse = {}
    finalResponse["Pests"] = jsonArray

    responseObject = {}
    responseObject['statusCode'] = 200
    responseObject['headers'] = {'Content-Type': 'application/json'}
    responseObject['body'] = json.dumps(finalResponse)

    return responseObject

# get all pest information

import json
import pymysql

endpoint = "pestsdb.cukmz8althst.us-east-1.rds.amazonaws.com"
username = "admin"
password = "960205yft"
database_name = "PestInfoDB"
connection = pymysql.connect(endpoint, user=username, passwd=password, db=database_name)


def lambda_handler(event, context):
    cursor = connection.cursor()
    cursor.execute('SELECT * from Pest')

    rows = cursor.fetchall()

    jsonArray = []
    for row in rows:
        jsonobj = {}
        jsonobj['pestId'] = row[0]
        jsonobj['pestName'] = row[1]
        jsonobj['weight'] = row[2]
        jsonobj['height'] = row[3]
        jsonobj['threat'] = row[4]
        jsonobj['country'] = row[5]
        jsonobj['category'] = row[6]
        jsonobj['diet'] = row[7]
        jsonobj['ways'] = row[8]
        jsonobj['tips'] = row[9]
        jsonobj['imageURL'] = row[10]
        jsonobj['score'] = row[11]

        jsonArray.append(jsonobj)

    finalResponse = {}
    finalResponse["Pests"] = jsonArray

    responseObject = {}
    responseObject['statusCode'] = 200
    responseObject['headers'] = {'Content-Type': 'application/json'}
    responseObject['body'] = json.dumps(finalResponse)

    return responseObject

# get all pest location
import json
import pymysql

endpoint = "pestsdb.cukmz8althst.us-east-1.rds.amazonaws.com"
username = "admin"
password = "960205yft"
database_name = "PestInfoDB"
connection = pymysql.connect(endpoint, user=username, passwd=password, db=database_name)


def lambda_handler(event, context):
    state = event['queryStringParameters']['state']
    cursor = connection.cursor()
    if state == "nostate":
        cursor.execute(
            'select l.location_Name, l.latitude,l.lontitude , p.pest_category,p.pest_name,l.State from Pest p inner join Location l on p.pest_id = l.pest_ID ')
    else:
        cursor.execute(
            'select l.location_Name, l.latitude,l.lontitude , p.pest_category,p.pest_name ,l.State from Pest p inner join Location l on p.pest_id = l.pest_ID where l.State = %s ',
            (state))
    rows = cursor.fetchall()

    jsonArray = []
    for row in rows:
        jsonobj = {}
        jsonobj['city'] = row[0]
        jsonobj['latitude'] = row[1]
        jsonobj['longtitude'] = row[2]
        jsonobj['category'] = row[3]
        jsonobj['name'] = row[4]
        jsonobj['state'] = row[5]
        jsonArray.append(jsonobj)

    finalResponse = {}
    finalResponse["Pests"] = jsonArray

    responseObject = {}
    responseObject['statusCode'] = 200
    responseObject['headers'] = {'Content-Type': 'application/json'}
    responseObject['body'] = json.dumps(finalResponse)

    return responseObject

# get report information


import json
import pymysql

endpoint = "pestsdb.cukmz8althst.us-east-1.rds.amazonaws.com"
username = "admin"
password = "960205yft"
database_name = "PestInfoDB"
connection = pymysql.connect(endpoint, user=username, passwd=password, db=database_name)


def lambda_handler(event, context):
    cursor = connection.cursor()
    cursor.execute(
        'select l.State, pest_category, count(pest_category) as Amount from Pest p inner join Location l on p.pest_id = l.pest_ID group by p.pest_category, l.State')

    rows = cursor.fetchall()

    jsonArray = []
    for row in rows:
        jsonobj = {}
        jsonobj['state'] = row[0]
        jsonobj['category'] = row[1]
        jsonobj['number'] = row[2]

        jsonArray.append(jsonobj)

    finalResponse = {}
    finalResponse["Pests"] = jsonArray

    responseObject = {}
    responseObject['statusCode'] = 200
    responseObject['headers'] = {'Content-Type': 'application/json'}
    responseObject['body'] = json.dumps(finalResponse)

    return responseObject