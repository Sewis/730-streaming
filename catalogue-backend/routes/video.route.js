const express = require('express');
const app = express();
const videoRoute = express.Router();

let Video = require('../models/Video');

// Add Video
videoRoute.route('/create').post((req, res, next) => {
  Video.create(req.body, (error, data) => {
    if (error) {
      return next(error)
    } else {
      res.json(data)
    }
  })
});

// Get All Videos
videoRoute.route('/').get((req, res) => {
  Video.find((error, data) => {
    if (error) {
      return next(error)
    } else {
      res.json(data)
    }
  })
})

// Get single Video
videoRoute.route('/read/:id').get((req, res) => {
  Video.findById(req.params.id, (error, data) => {
    if (error) {
      return next(error)
    } else {
      res.json(data)
    }
  })
})


// Update Video
videoRoute.route('/update/:id').put((req, res, next) => {
  Video.findByIdAndUpdate(req.params.id, {
    $set: req.body
  }, (error, data) => {
    if (error) {
      return next(error);
      console.log(error)
    } else {
      res.json(data)
      console.log('Data updated successfully')
    }
  })
})

// Delete Video
videoRoute.route('/delete/:id').delete((req, res, next) => {
  Video.findOneAndRemove(req.params.id, (error, data) => {
    if (error) {
      return next(error);
    } else {
      res.status(200).json({
        msg: data
      })
    }
  })
})

module.exports = videoRoute;