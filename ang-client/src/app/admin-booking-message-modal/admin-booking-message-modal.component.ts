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
  @Input() mode : any;
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
    let notesOffset = 0;//this.notes.length;
    this.networkService.getNotes(this.message, notesOffset).subscribe(
      data => {this.notes = data;},
      error => console.log('error: ' + error)
    );
  }

  submitMessage() {
    this.networkService.postNote(this.id, this.message).subscribe(
      data => {this.message = null; this.modalRef.close();},
      error => console.log('error:'+error)
    );
  }

}
