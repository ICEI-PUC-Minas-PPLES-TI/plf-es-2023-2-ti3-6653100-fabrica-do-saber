import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'age'
})
export class AgePipe implements PipeTransform {
  transform(birthDate: string): number | null {

    if (!birthDate)
      return null;

    const [day, month, year] = birthDate.split('/').map(Number);
    const bd: Date = new Date(year, month - 1, day);
    const today: Date = new Date();
    const age: number = today.getFullYear() - bd.getFullYear();

    if (today.getMonth() < bd.getMonth() || (today.getMonth() === bd.getMonth() && today.getDate() < bd.getDate()))
      return age - 1;

    return age;
  }
}
