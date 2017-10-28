var express = require('express');
var bodyParser = require('body-parser');
var path = require('path');

var app = express();

/*var logger= function(req,res,next){
 console.log('Logging...');
 next();
 };
 
 app.use(logger);*/

/*
 * var people = [{
        name: "Jeff",
        age: 30
    },
    {
        name: "John",
        age: 22
    },
    {
        name: "Anna",
        age: 30
    }
]
        ;*/

//View Engine
app.set('view engine','ejs');
app.set('views',path.join(__dirname,'views'));

//body parser middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

//Set Static Path
app.use(express.static(path.join(__dirname, 'public')));

var users =  [
    {
        id:1,
        first_name:'John',
        last_name:'Doe',
        email:'johndoe@gmail.com'
    },
    {
        id:2,
        first_name:'Bob',
        last_name:'Smith',
        email:'bobsmith@gmail.com'
    },
    {
        id:3,
        first_name:'Jill',
        last_name:'Jackson',
        email:'jilljackson@gmail.com'
    }
];

app.get('/', function (req, res) {
    var title='Customers';
    res.render('index',{
        title:'Customers',
        users: users
    });
});

app.post('/users/add',function(req,res){
    console.log('FORM SUBMITTED');
});

app.listen(8080, function () {
    console.log("Server avviato sulla porta 8080");
});
