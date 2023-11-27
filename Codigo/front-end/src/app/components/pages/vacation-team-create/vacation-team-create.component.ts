import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs';
import { VacationTeam } from '../../../interfaces/Vacation-team';
import { VacationTeamImp } from '../../../classes/vacation-team/vacation-team-imp';
import { VacationTeamService } from '../../../services/vacation-team/vacation-team.service';

@Component({
  selector: 'app-vacation-team-create',
  templateUrl: './vacation-team-create.component.html',
  styleUrls: ['./vacation-team-create.component.css']
})
export class VacationTeamCreateComponent {

  vacationTeam: VacationTeam = new VacationTeamImp();

  constructor(private router: Router, private vacationTeamService: VacationTeamService) {
  }

  createVacationTeam(): void {
    const formattedTeam = this.formatToRequest(this.vacationTeam);
    let op: boolean = confirm('Deseja criar a creche de férias?');
    if (op)
      this.vacationTeamService.createVacationTeam(formattedTeam)
        .pipe(
          tap((response): void => {
            this.router.navigate(['/vacation-team-list']);
          }),
          catchError(err => {
            throw err;
          })
        )
        .subscribe();
  }

  cancel(): void {
    this.router.navigate(['/vacation-team-list']);
  }

  formatToRequest(vacationTeam: VacationTeam) {
    return {
      name: vacationTeam.name,
      grade: vacationTeam.grade,
      classroom: vacationTeam.classroom,
      startDate: vacationTeam.startDate,
      endDate: vacationTeam.endDate,
      teacher: {
        id: vacationTeam.teacherId
      },
      studentIds: vacationTeam.studentIds.map((studentId: number) => studentId)
    };
  }

}
