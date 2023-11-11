import { Component } from '@angular/core';
import { Transaction } from '../../../interfaces/Transaction';
import { Router } from '@angular/router';
import { TransactionService } from '../../../services/transaction/transaction.service';

@Component({
  selector: 'app-transaction-form',
  templateUrl: './transaction-form.component.html',
  styleUrls: ['./transaction-form.component.css']
})
export class TransactionFormComponent {

  transaction !: Transaction;

  constructor(private router: Router, private transactionService: TransactionService) {
  }

  createTransaction(): void {

  }

  cancel() : void{
    this.router.navigate(['/transaction-list']);
  }
}
