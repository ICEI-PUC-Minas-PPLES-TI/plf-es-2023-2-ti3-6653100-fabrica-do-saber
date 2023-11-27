import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, tap } from 'rxjs';
import { VacationTeam } from '../../../interfaces/Vacation-team';
import { VacationTeamService } from '../../../services/vacation-team/vacation-team.service';

@Component({
  selector: 'app-vacation-team-edit',
  templateUrl: './vacation-team-edit.component.html',
  styleUrls: ['./vacation-team-edit.component.css']
})
export class VacationTeamEditComponent {

  vacationTeam !: VacationTeam;
  vacationTeamId: number = 0;

  constructor(private route: ActivatedRoute, private vacationTeamService: VacationTeamService, private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params): void => {
      this.vacationTeamId = parseInt(<string>params.get('id'));
      this.getVacationTeamById(this.vacationTeamId);
    });
  }

  getVacationTeamById(id: number): void {
    this.vacationTeamService.getVacationTeamById(id).subscribe((vacationTeam: VacationTeam): void => {
      this.vacationTeam = vacationTeam;
    });
  }

  updateVacationTeam(): void {
    const formattedTeam = this.formatToRequest(this.vacationTeam);
    let op: boolean = confirm('Deseja atualizar a creche de férias?');
    if (op) {
      this.vacationTeamService.updateVacationTeam(this.vacationTeamId, formattedTeam)
        .pipe(
          tap((response): void => {
            this.router.navigate(['/vacation-team-list']);
          }),
          catchError(err => {
            throw err;
          }))
        .subscribe();
      this.router.navigate(['/vacation-team-list']);
    }
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
