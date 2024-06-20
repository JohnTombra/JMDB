package com.novaapps.jmdb.data.model

class Notes {

    /*



/*



Base url: http://jmdb.eu-north-1.elasticbeanstalk.com/

/api/login

Request Object (JSON)


{
    "username": "admin@demo",
    "password": "1234567"
}

Response  Object

{
    "status": true,
    "message": "Login successfully111111",
    "data": {
        "httpOnly": true,
        "masAge": 86400000
    },
    "jwt": "jwt",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjcxNjMsImlhdCI6MTcxNTI5MTIzOSwiZXhwIjoxNzE1Mzc3NjM5fQ.R8VrMsLJofpuxRa5X2KnAVU9b-rbHGlovxBrt28_vEY",
    "user": {
        "status": true,
        "user": {
            "id": 7163,
            "group_id": 111111,
            "name": "Paul  Jude Ikwobe",
            "username": "admin@DEMO",
            "password": "$2a$10$3vbPNNlRte.sboByjrOlUOKFDZq5U/jDNnRYl7wqNGXXKawqM4dOW",
            "department_id": null,
            "email": "ikwobejude@gmail.com",
            "user_phone": "08136759553",
            "remember_token": null,
            "service_id": "2147483647",
            "service_code": "DEMO",
            "created_at": "2023-02-16T08:42:43.000Z",
            "updated_at": "2024-04-19T05:28:04.000Z",
            "allowmobilelogin": 1,
            "allowdesktoplogin": 1,
            "first_use": 1,
            "agency_id": null,
            "tax_office_id": 22220032,
            "user_code": null,
            "surname": "Paul ",
            "firstname": "Jude",
            "middlename": "Ikwobe",
            "inactive": 1,
            "is_verified": 0
        }
    }
}






//////////////




Tin Verification API

Note: It can be phone number or Tin
Endpoint: /api/verify_tin_phone?phone=08136759553
Method: GET

Response Object

{
    "status": true,
    "res1": {
        "status": "success",
        "message": "Unique TIN found!",
        "title": null,
        "name": null,
        "first_name": "Paul",
        "middle_name": " ",
        "surname": "Jude",
        "tin": "23320765908",
        "phoneNumber": "08136759553",
        "address": null,
        "account_type": "individual"
    }
}






/////////////




Get Country API

Endpoint URL:  /api/fetch_countries
Method: GET

Response object

{
    "status": true,
    "data": [
        {
            "country_id": 1,
            "country": "NIGERIA",
            "country_code": "234"
        }
    ]
}




///////////////



Get State API

Endpoint url
/api/fetch_states

Method: GET

Response Object:

{
    "status": true,
    "data": [
        {
            "state_id": 1,
            "state": "ABIA",
            "state_code": "01",
            "country_id": 1
        }
    ]
}





//////////////////




STEP 1 FORM
End point:  /api/step1
Method: POST

Request Object:

{
    "application_type": "New Proposal",
    "title": "Mr",
    "fn": "Jude",
    "mn": "",
    "sn": "Paul",
    "gender": "Male",
    "dob": "22/04/1997",
    "occupation": "Developer",
    "nationality": "Nigeria",
    "soo": "1",
    "lga": "1",
    "phone1": "08136759553",
    "phone2": "",
    "phone3": "",
    "email": "ikwobejude@gmail.com",
    "tin": "23320765908",
    "identification": "nationality ID",
    "id_number": "5757588577575",
    "track_no": "12338847484884" // optional at first
}

Response Object

{
    "status": true,
    "track_no": "LVZRLK9O",
    "message": "Step 1 registration saved successfully"
}




////////////////






STEP 2 FORM
End point:  /api/step2
Method: POST

Request Object:

{
    "house_no": "40",
    "street_name": "John D Street",
    "district": "Kuje District",
    "co": "NILL",
    "addition_address_info": "SHADADI KUJE ABUJA",
    "pmb": "293",
    "track_no": "LVZRLK9O"
}

Response Object

{
    "status": true,
    "message": "Residential address saved",
    "track_no": "LVZRLK9O"
}





/////////////////





STEP 3 FORM
End point:  /api/step3
Method: POST

Request Object:

{
    "rfn": "Samuel",
    "rsn": "Enemona",
    "rmn": "Paul",
    "rphone1": "09067672044",
    "rphone2": "",
    "rphone3": "",
    "remail": "samueliwkobe31@gmail.com",
    "track_no": "LVZRLK9O",
    "rid": "NIN",
    "rid_number": "34847477473"
}

Response Object

{
    "status": true,
    "message": "Representative Info saved",
    "track_no": "LVZRLK9O"
}




////////////////



STEP 4 FORM
End point:  /api/step4
Method: POST

Request Object:

{
    "rhouse_no": "4746",
    "rstreet_name": "Dafara Angwa",
    "rdistrict": "Adj Capital science academic",
    "rcity_town": "8",
    "rstate": "3",
    "rcountry": "Nigeria",
    "rpmb": "98",
    "rco": "Nill",
    "radditinal_address_info": "Dafara Junction Adj Capital science academic",
    "track_no": "LVZRLK9O"
}

Success Response Object

{
    "status": true,
    "message": "Representative address detail saved",
    "track_no": "LVZRLK9O"
}










//////////////////




STEP 5 FORM
End point:  /api/step5
Method: POST

Request Object:

{
    "purpose": "Commercial",
    "plot__land_use": "Rental",
    "plot_district": "Adj Capital science academic",
    "plot_lga": "Kuje",
    "plot_address_discription": "Shadadi close beside Living faith church Kuje Abuja",
    "track_no": "LVZRLK9O"
}

Success Response Object

{
    "status": true,
    "message": "PLOT detail saved",
    "track_no": "LVZRLK9O"
}

Error Response Object

{
    "status": false,
    "message": "House No is required"
}



//////////////////

//CONGRATULATIONS, YOU HAVE COMPLETED THE REGISTRATION...HOME

data.fileName == "Copy of the certificate of occupancy"
data.fileName == "Copy of MLS TP Acknowledge Letter"
data.fileName == "Copy of Structural Drawing and Details"
(data.fileName == "Copy of Structural Calculations")
data.fileName == "Copy of Architectural Drawing and Details"
data.fileName == "Copy of Mechanical Electrical Drawings and Details")
data.fileName == "Site Analysis Report"
(data.fileName == "Copy of EIAR")
data.fileName == "Copy of BLOCK Plan")
data.fileName == "Soil Investigation Report")
data.fileName == "Copy of Service Approvals")





////////////////////




STEP 6 FORM
End point:  /api/step6
Method: POST

Request Object:

{
    "fileName": "T/oACtt0uK1sADCaxbpcZWkAAxUqpVoAAmtwNV0ABltaCWoABMWrDVAAoToAFIAAaAAAoAAAAAAAIAB/9k=", // base64 url
    "track_no": "LVZRLK9O",
    "fileUrl": "Passport",
}

Success Response Object

{
    "status": true,
    "message": "Uploaded successfully"
}




* */





    */
}