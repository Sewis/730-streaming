import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../service/api.service';

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

  removeVideo(video, index) {
    if(window.confirm('Are you sure?')) {
        this.apiService.deleteVideo(video._id).subscribe((data) => {
          this.Video.splice(index, 1);
        }
      )    
    }
  }

}