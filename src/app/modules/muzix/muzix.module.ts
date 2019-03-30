import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { CardContainerComponent } from './components/card-container/card-container.component';
import { CardComponent } from './components/card/card.component';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from '@angular/material/tooltip';
import { HeaderComponent } from './components/header/header.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatMenuModule} from '@angular/material/menu';
import { AppRoutingModule } from '../../app-routing.module';
import { WishListComponent } from './components/wish-list/wish-list.component';
import { FooterComponent } from './components/footer/footer.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field'
import { DialogComponent } from './components/dialog/dialog.component';


@NgModule({
  declarations: [
    CardContainerComponent, 
    CardComponent, 
    HeaderComponent, 
    WishListComponent, 
    FooterComponent, 
    DialogComponent
  ],
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    BrowserAnimationsModule,
    MatTooltipModule,
    MatToolbarModule,
    MatMenuModule,
    AppRoutingModule,
    MatSnackBarModule,
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatFormFieldModule
  ],

  exports: [
    CardContainerComponent,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    HeaderComponent,
    AppRoutingModule,
    FooterComponent,
    WishListComponent
  ],

  entryComponents: [
    DialogComponent
  ],

})
export class MuzixModule { }
