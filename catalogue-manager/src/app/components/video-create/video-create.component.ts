import { Router } from '@angular/router';
import { ApiService } from '../../service/api.service';
import { Component, OnInit, NgZone } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: 'app-video-create',
  templateUrl: './video-create.component.html',
  styleUrls: ['./video-create.component.css']
})

export class VideoCreateComponent implements OnInit {  
  submitted = false;
  videoForm: FormGroup;
  VideoProfile:any = ['Finance', 'BDM', 'HR', 'Sales', 'Admin']
  
  constructor(
    public fb: FormBuilder,
    private router: Router,
    private ngZone: NgZone,
    private apiService: ApiService
  ) { 
    this.mainForm();
  }

  ngOnInit() { }

  mainForm() {
    this.videoForm = this.fb.group({
      title: ['', [Validators.required]],
      category: ['', [Validators.required]],
    })
  }

  // Choose designation with select dropdown
  updateProfile(e){
    this.videoForm.get('category').setValue(e, {
      onlySelf: true
    })
  }

  // Getter to access form control
  get myForm(){
    return this.videoForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (!this.videoForm.valid) {
      return false;
    } else {
      this.videoForm.value.uploadDate = new Date();
      this.apiService.createVideo(this.videoForm.value).subscribe(
        (res) => {
          console.log('Video successfully created!')
          this.ngZone.run(() => this.router.navigateByUrl('/videos-list'))
        }, (error) => {
          console.log(error);
        });
    }
  }

}
