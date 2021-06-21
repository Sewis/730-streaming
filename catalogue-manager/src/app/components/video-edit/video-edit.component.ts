import { Video } from '../../model/Video';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ApiService } from '../../service/api.service';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";


@Component({
  selector: 'app-video-edit',
  templateUrl: './video-edit.component.html',
  styleUrls: ['./video-edit.component.css']
})

export class VideoEditComponent implements OnInit {
  submitted = false;
  editForm: FormGroup;
  videoData: Video[];
  VideoProfile:any = ['Sport', 'Technology', 'Nature', 'Food', 'Children', 'Science']

  constructor(
    public fb: FormBuilder,
    private actRoute: ActivatedRoute,
    private apiService: ApiService,
    private router: Router
  ) {}

  ngOnInit() {
    this.updateVideo();
    let id = this.actRoute.snapshot.paramMap.get('id');
    this.getVideo(id);
    this.editForm = this.fb.group({
      title: ['', [Validators.required]],
      category: ['', [Validators.required]],
    })
  }

  // Choose options with select-dropdown
  updateProfile(e) {
    this.editForm.get('category').setValue(e, {
      onlySelf: true
    })
  }

  // Getter to access form control
  get myForm() {
    return this.editForm.controls;
  }

  getVideo(id) {
    this.apiService.getVideo(id).subscribe(data => {
      this.editForm.setValue({
        title: data['title'],
        category: data['category']
      });
    });
  }

  updateVideo() {
    this.editForm = this.fb.group({
      title: ['', [Validators.required]],
      category: ['', [Validators.required]],
    })
  }

  onSubmit() {
    this.submitted = true;
    if (!this.editForm.valid) {
      return false;
    } else {
      let id = this.actRoute.snapshot.paramMap.get('id');
      this.apiService.updateVideo(id, this.editForm.value)
        .subscribe(res => {
          this.router.navigateByUrl('/videos-list');
          console.log('Content updated successfully!')
        }, (error) => {
          console.log(error)
        })
    }
  }

}