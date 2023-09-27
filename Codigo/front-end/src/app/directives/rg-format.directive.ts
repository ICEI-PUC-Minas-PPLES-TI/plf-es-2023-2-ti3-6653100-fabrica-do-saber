import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appRgFormatter]'
})
export class RgFormatDirective {

  constructor(private el: ElementRef) { }

  @HostListener('input', ['$event']) onInput(event: InputEvent): void {

    const inputElement: HTMLInputElement = this.el.nativeElement as HTMLInputElement;
    const value: string = inputElement.value.replace(/\D/g, '');

    inputElement.value = this.formatRg(value);
  }

  private formatRg(value: string): string {
    if (value.length <= 2) {
      return value;
    } else if (value.length <= 5) {
      return `${value.slice(0, 2)}.${value.slice(2)}`;
    } else {
      return `${value.slice(0, 2)}.${value.slice(2, 5)}.${value.slice(5, 8)}`;
    }
  }

}
