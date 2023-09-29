import {Directive, ElementRef, HostListener, Input, Renderer2} from '@angular/core';

@Directive({
  selector: '[appCurrencyFormat]'
})
export class CurrencyFormatDirective {

  constructor(private el: ElementRef, private renderer: Renderer2) {
  }

  @HostListener('input', ['$event']) onInput(event: InputEvent): void {

    const inputElement: HTMLInputElement = this.el.nativeElement as HTMLInputElement;
    const value: string = inputElement.value;
    const numericValue: string = value.replace(/\D/g, '');
    const numberValue: number = parseFloat(numericValue) / 100;
    const formattedValue: string = this.formatCurrency(numberValue);

    this.renderer.setProperty(inputElement, 'value', formattedValue);
  }

  private formatCurrency(value: number): string {
    return value.toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    });
  }
}
