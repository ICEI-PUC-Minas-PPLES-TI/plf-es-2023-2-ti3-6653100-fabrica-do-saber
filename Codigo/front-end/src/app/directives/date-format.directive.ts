import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[appDateFormat]'
})
export class DateFormatDirective {

  constructor(private el: ElementRef) {
  }

  @HostListener('input', ['$event'])
  onInput(event: InputEvent): void {

    const inputElement: HTMLInputElement = this.el.nativeElement as HTMLInputElement;
    const value: string = inputElement.value.replace(/\D/g, '');

    inputElement.value = this.formatDate(value);
  }

  private formatDate(value: string): string {

    if (value.length <= 2) {
      return value;
    } else if (value.length <= 4) {
      return `${value.slice(0, 2)}/${value.slice(2)}`;
    } else {
      return `${value.slice(0, 2)}/${value.slice(2, 4)}/${value.slice(4, 8)}`;
    }
  }

}
