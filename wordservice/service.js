const express = require('express')
const app = express()

app.get('/word/:word', (req, res) => {
    const { word } = req.params;
    res.send(`you send the word ${word}`);
})

app.listen(3000, () => console.log('Example app listening on port 3000!'))