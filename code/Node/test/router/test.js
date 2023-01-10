const express = require("express");
const { locals } = require("../app");
const router = express();
const config = require("./config");
const base_url = "http://"+ config.IP + ":" + config.PORT + "/images/";

console.log(config);


router.get("/", (req, res)=>{
    var data = {
        "code":"0",
        "mesage":"success",
        "data":[
            {
                "image": base_url + "4.jpg"
            }
        ]
    };
    res.send(data);
});

module.exports = router;