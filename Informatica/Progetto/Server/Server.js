var http = require("http"),
       fs = require("fs");
       require('./jquery-3.2.1.js');
       require( './logBtn.js');
      
fs.readFile(__dirname + "/Home.html", function (err, html) {
    if (err) {
        throw err;
    }
    http.createServer(function (request, response) {
        response.writeHeader(200, {"Content-Type": "text/html"});
        response.write(html);
        response.end();
    }).listen(8080);
});


console.log('Server running at http://127.0.0.1:8080/');