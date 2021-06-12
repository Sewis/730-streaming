const mongoose = require('mongoose');
const Schema = mongoose.Schema;

let Video = new Schema({
   title: {
      type: String
   },
   uploadDate: {
      type: Date
   },
   category: {
      type: String
   },
   length: {
      type: Number
   },
   urlID: {
      type: String
   }
}, {
   collection: 'videos'
})

module.exports = mongoose.model('Video', Video)