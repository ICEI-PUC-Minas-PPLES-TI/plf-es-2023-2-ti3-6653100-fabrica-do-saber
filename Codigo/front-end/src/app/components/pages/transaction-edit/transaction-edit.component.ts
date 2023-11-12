import { Component, Input } from '@angular/core';
import { Transaction } from '../../../interfaces/Transaction';
import { Router } from '@angular/router';
import { TransactionService } from '../../../services/transaction/transaction.service';
import { TeacherService } from '../../../services/teacher/teacher.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { catchError, tap } from 'rxjs';

@Component({
  selector: 'app-transaction-edit',
  templateUrl: './transaction-edit.component.html',
  styleUrls: ['./transaction-edit.component.css']
})
export class TransactionEditComponent {

  transaction !: Transaction;
  @Input() transactionId!: number;

  constructor(private router: Router, private transactionService: TransactionService, private teacherService: TeacherService, private activeModal: NgbActiveModal) {
  }

  ngOnInit(): void {
    if (this.transactionId)
      this.getTransactionById(this.transactionId);
  }

  getTransactionById(id: number): void {
    this.transactionService.getTransactionById(id).subscribe((transaction: Transaction): void => {
      this.transaction = transaction;
    });
  }

  updateTransaction(): void {
    let op: boolean = confirm('Deseja atualizar a transação?');
    if (op) {
      this.transactionService.updateTransaction(this.transactionId, this.transaction)
        .pipe(
          tap((response): void => {
            this.activeModal.close();
          }),
          catchError(err => {
            throw err;
          }))
        .subscribe();
      this.activeModal.close();
    }
  }

  cancel(): void {
    this.activeModal.close();
  }
}
