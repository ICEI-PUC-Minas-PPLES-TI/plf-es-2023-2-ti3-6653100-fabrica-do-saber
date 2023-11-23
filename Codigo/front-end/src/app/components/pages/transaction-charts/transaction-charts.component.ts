import {Component, ElementRef, ViewChild} from '@angular/core';
import {TransactionService} from '../../../services/transaction/transaction.service';
import {Transaction} from '../../../interfaces/Transaction';
import {Chart, ChartConfiguration, registerables} from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-transaction-charts',
  templateUrl: './transaction-charts.component.html',
  styleUrls: ['./transaction-charts.component.css']
})
export class TransactionChartsComponent {

  originalTransactions!: Transaction[];
  transactions!: Transaction[];
  financialFlowTypes: string[] = ['Entrada', 'Saída'];
  income!: number;
  outcome!: number;
  totalBalance !: number;
  months: string[] = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];

  constructor(private transactionService: TransactionService) {
  }

  ngOnInit(): void {
    this.getTransactions();
    this.calculateTotalBalance();
  }

  @ViewChild('TransactionChart') transactionChartRef!: ElementRef;

  private generateTransactionChart(): void {
    const ctx = this.transactionChartRef.nativeElement.getContext('2d');
    const data = {
      labels: this.months,
      datasets: [
        {
          label: 'Receitas',
          data: this.getDataArray(this.financialFlowTypes[0]),
          borderColor: 'green',
          backgroundColor: 'green',
        },
        {
          label: 'Despesas',
          data: this.getDataArray(this.financialFlowTypes[1]),
          borderColor: 'red',
          backgroundColor: 'red',
        }
      ]
    };
    new Chart(ctx, {
      type: 'bar',
      data: data,
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      },
    } as ChartConfiguration);
  }

  @ViewChild('CategoryChart') categoryChartRef!: ElementRef;

  private generateCategoryChart(): void {
    const ctx = this.categoryChartRef.nativeElement.getContext('2d');
    const data = {
      labels: this.months,
      datasets: [
        {
          label: 'Receitas',
          data: this.getDataArray(this.financialFlowTypes[0]),
          borderColor: 'green',
          backgroundColor: 'green',
        },
        {
          label: 'Despesas',
          data: this.getDataArray(this.financialFlowTypes[1]),
          borderColor: 'red',
          backgroundColor: 'red',
        }
      ]
    };
    new Chart(ctx, {
      type: 'bar',
      data: data,
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      },
    } as ChartConfiguration);
  }

  getDataArray(financialFlowType: string): number[] {
    const monthlyTotals: number[] = Array.from({length: 12}, (): number => 0);
    this.originalTransactions
      .filter((transaction: Transaction): boolean => {
        const transactionYear: number = new Date(this.formatDate(transaction.date)).getFullYear();
        const now: number = new Date().getFullYear();
        return (
          transaction.financialFlowType.toLowerCase() === financialFlowType.toLowerCase() &&
          (now - transactionYear) <= 1
        );
      })
      .forEach((transaction: Transaction): void => {
        const transactionMonth: number = new Date(this.formatDate(transaction.date)).getMonth();
        monthlyTotals[transactionMonth] += Math.abs(transaction.value);
      });
    return monthlyTotals;
  }

  formatDate(dateString: string): string {
    const part = dateString.split('/');
    return `${part[2]}-${part[1]}-${part[0]}`;
  }

  getTransactions(): void {
    this.transactionService.getTransactions().subscribe((transactions: Transaction[]): void => {
      this.originalTransactions = transactions;
      this.transactions = [...this.originalTransactions];
      this.income = this.getFinancialFlowTypeTotal(this.financialFlowTypes[0]);
      this.outcome = this.getFinancialFlowTypeTotal(this.financialFlowTypes[1]);
      this.generateTransactionChart();
      this.generateCategoryChart();
    });
  }

  calculateTotalBalance(): void {
    this.transactionService.getTotal().subscribe((total: number): void => {
      this.totalBalance = total;
    });
  }

  getFinancialFlowTypeTotal(financialFlowType: string): number {
    return this.originalTransactions
      .filter((transaction: Transaction): boolean => transaction.financialFlowType.toLowerCase() === financialFlowType.toLowerCase())
      .map((transaction: Transaction) => transaction.value)
      .reduce((total: number, value: number) => total + value, 0);
  }
}
