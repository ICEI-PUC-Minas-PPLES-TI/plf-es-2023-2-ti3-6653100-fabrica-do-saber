import { Component } from '@angular/core';
import { catchError, tap } from 'rxjs';
import { Router } from '@angular/router';
import { TransactionService } from '../../../services/transaction/transaction.service';
import { Transaction } from '../../../interfaces/Transaction';
import { TransactionImp } from '../../../classes/transaction/transaction-imp';
import { TeacherService } from '../../../services/teacher/teacher.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-transaction-create',
  templateUrl: './transaction-create.component.html',
  styleUrls: ['./transaction-create.component.css']
})
export class TransactionCreateComponent {

  transaction: Transaction = new TransactionImp();

  constructor(private router: Router, private transactionService: TransactionService, private teacherService: TeacherService, private activeModal: NgbActiveModal) {
  }

  createTransaction(): void {
    this.transaction.value = this.teacherService.formatCurrency(this.transaction.value);
    console.log(this.transaction);
    let op: boolean = confirm('Deseja criar a transação?');
    if (op)
      this.transactionService.createTransaction(this.transaction)
        .pipe(
          tap((response): void => {
            this.activeModal.close();
          }),
          catchError(err => {
            throw err;
          })
        )
        .subscribe();
  }

  cancel(): void {
    this.activeModal.close();
  }
}
