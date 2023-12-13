import { Component, ElementRef, ViewChild } from '@angular/core';
import { TransactionService } from '../../../services/transaction/transaction.service';
import { Transaction } from '../../../interfaces/Transaction';
import { Chart, ChartConfiguration, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-transaction-charts',
  templateUrl: './transaction-charts.component.html',
  styleUrls: ['./transaction-charts.component.css']
})
export class TransactionChartsComponent {

  originalTransactions!: Transaction[];
  transactions!: Transaction[];
  financialFlowTypes: string[] = ['INPUT', 'OUTPUT'];
  income!: number;
  outcome!: number;
  totalBalance !: number;
  months: string[] = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
  categories: string[] = [
    'PAYROLL',
    'INFRASTRUCTURE_EXPENSE',
    'INSTITUTIONAL_MARKETING',
    'EDUCATIONAL_PROJECTS',
    'ADMINISTRATIVE_COSTS',
    'SCHOOL_EVENTS',
    'MAINTENANCE_SERVICES',
    'EDUCATIONAL_MATERIAL',
  ];
  @ViewChild('TransactionChart') transactionChartRef!: ElementRef;
  @ViewChild('CategoryChart') categoryChartRef!: ElementRef;

  constructor(private transactionService: TransactionService) {
  }

  ngOnInit(): void {
    this.getTransactions();
    this.calculateTotalBalance();
  }

  getArrayDataByCategory(category: string): number[] {
    let total: number[] = Array.from({length: 8}, (): number => 0);
    this.originalTransactions
      .filter((transaction: Transaction): boolean => {
        const transactionCategory: string = transaction.category;
        return transactionCategory === category;
      })
      .forEach((transaction: Transaction): void => {
        total[this.categories.indexOf(transaction.category)] += transaction.value;
      });
    return total;
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

  private generateTransactionChart(): void {
    const ctx = this.transactionChartRef.nativeElement.getContext('2d');
    const data = {
      labels: this.months,
      datasets: [
        {
          label: 'Receitas',
          data: this.getDataArray(this.financialFlowTypes[0]),
          borderColor: 'green',
          backgroundColor: 'mediumseagreen',
          borderRadius: Number(3),
        },
        {
          label: 'Despesas',
          data: this.getDataArray(this.financialFlowTypes[1]),
          borderColor: 'red',
          backgroundColor: 'indianred',
          borderRadius: Number(3),
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

  private generateCategoryChart(): void {
    const ctx = this.categoryChartRef.nativeElement.getContext('2d');
    const data = {
      labels: this.categories,
      datasets: [
        {
          label: 'Pagamento aos funcionários',
          data: this.getArrayDataByCategory('PAYROLL'),
          borderColor: 'green',
          backgroundColor: 'mediumseagreen',
          borderRadius: Number(3),
        },
        {
          label: 'Despesas em infraestrutura',
          data: this.getArrayDataByCategory('INFRASTRUCTURE_EXPENSE'),
          borderColor: 'red',
          backgroundColor: 'indianred',
          borderRadius: Number(3),
        },
        {
          label: 'Marketing institucional',
          data: this.getArrayDataByCategory('INSTITUTIONAL_MARKETING'),
          borderColor: 'blue',
          backgroundColor: 'lightblue',
          borderRadius: Number(3),
        },
        {
          label: 'Projetos educacionais',
          data: this.getArrayDataByCategory('EDUCATIONAL_PROJECTS'),
          borderColor: 'yellow',
          backgroundColor: 'tan',
          borderRadius: Number(3),
        },
        {
          label: 'Custos administrativos',
          data: this.getArrayDataByCategory('ADMINISTRATIVE_COSTS'),
          borderColor: 'orange',
          backgroundColor: 'lightsalmon',
          borderRadius: Number(3),
        },
        {
          label: 'Eventos escolares',
          data: this.getArrayDataByCategory('SCHOOL_EVENTS'),
          borderColor: 'purple',
          backgroundColor: 'lightpink',
          borderRadius: Number(3),
        },
        {
          label: 'Custos de manutenção',
          data: this.getArrayDataByCategory('MAINTENANCE_SERVICES'),
          borderColor: 'darkorchid',
          backgroundColor: 'darkorchid',
          borderRadius: Number(3),
        },
        {
          label: 'Material escolar',
          data: this.getArrayDataByCategory('EDUCATIONAL_MATERIAL'),
          borderColor: 'khaki',
          backgroundColor: 'khaki',
          borderRadius: Number(3),
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
          },
          x: {
            ticks: {
              display: false
            }
          }
        },
      },
    } as ChartConfiguration);
  }
}
