import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TemplateComponent } from './template.component';
import { TemplateDetailComponent } from './template-detail.component';
import { TemplatePopupComponent } from './template-dialog.component';
import { TemplateDeletePopupComponent } from './template-delete-dialog.component';

import { Principal } from '../../shared';

export const templateRoute: Routes = [
  {
    path: 'template',
    component: TemplateComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.template.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'template/:id',
    component: TemplateDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.template.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const templatePopupRoute: Routes = [
  {
    path: 'template-new',
    component: TemplatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.template.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'template/:id/edit',
    component: TemplatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.template.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'template/:id/delete',
    component: TemplateDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.template.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
