import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../service/api.service';
import { ImageMap } from '../../../assets/images/ImageMap';

@Component({
  selector: 'app-video-list',
  templateUrl: './video-list.component.html',
  styleUrls: ['./video-list.component.css']
})

export class VideoListComponent implements OnInit {
  
  Video:any = [];

  constructor(private apiService: ApiService) { 
    this.readVideo();
  }

  ngOnInit() {}

  readVideo(){
    this.apiService.getVideos().subscribe((data) => {
     this.Video = data;
    })    
  }

  getThumbnail(video)
  {
    return ImageMap.getThumbnail(video.title);
  }

  formatSeconds(seconds)
  {
    return new Date(seconds * 1000).toISOString().substr(11, 8)
  }
  
  removeVideo(video, index) {
    if(window.confirm('Are you sure?')) {
        this.apiService.deleteVideo(video._id).subscribe((data) => {
          this.Video.splice(index, 1);
        }
      )    
    }
  }

}