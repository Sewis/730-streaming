import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { VideoCreateComponent } from './components/video-create/video-create.component';
import { VideoListComponent } from './components/video-list/video-list.component';
import { VideoEditComponent } from './components/video-edit/video-edit.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'create-video' },
  { path: 'create-video', component: VideoCreateComponent },
  { path: 'edit-video/:id', component: VideoEditComponent },
  { path: 'videos-list', component: VideoListComponent }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }