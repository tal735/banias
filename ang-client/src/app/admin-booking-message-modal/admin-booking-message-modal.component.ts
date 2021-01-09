import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-admin-booking-message-modal',
  templateUrl: './admin-booking-message-modal.component.html',
  styleUrls: ['./admin-booking-message-modal.component.css']
})
export class AdminBookingMessageModalComponent implements OnInit {

  notes: any = [];
  message: string;

  @Input() id : any;
  @ViewChild('messagesModal') messagesModal: ElementRef;

  modalRef : NgbModalRef = null;
  
  constructor(private modalService: NgbModal, private networkService : NetworkService) { }

  ngOnInit(): void {
  }

  openModal() {
    this.modalRef = this.modalService.open(this.messagesModal, { size: 'sm', backdrop: 'static'});
    this.fetchNotes();
  }

  fetchNotes() {
    let maxId = null;
    if (this.notes.length > 0) {
      maxId = Math.max.apply(Math, this.notes.map(function(o) { return o.id; }));
    }
    this.networkService.adminGetNotes(this.id, maxId).subscribe(
      data => {this.notes = this.notes.concat(data);},
      error => console.log('error: ' + error)
    );
  }

  submitMessage() {
    this.networkService.adminPostNote(this.id, this.message).subscribe(
      data => {this.message = null; this.fetchNotes();},
      error => console.log('error:'+error)
    );
  }

}
