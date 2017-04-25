import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MailReceiverComponent } from './mail-receiver.component';
import { MailReceiverDetailComponent } from './mail-receiver-detail.component';
import { MailReceiverPopupComponent } from './mail-receiver-dialog.component';
import { MailReceiverDeletePopupComponent } from './mail-receiver-delete-dialog.component';

import { Principal } from '../../shared';

export const mailReceiverRoute: Routes = [
  {
    path: 'mail-receiver',
    component: MailReceiverComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.mailReceiver.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'mail-receiver/:id',
    component: MailReceiverDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.mailReceiver.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const mailReceiverPopupRoute: Routes = [
  {
    path: 'mail-receiver-new',
    component: MailReceiverPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.mailReceiver.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'mail-receiver/:id/edit',
    component: MailReceiverPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.mailReceiver.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'mail-receiver/:id/delete',
    component: MailReceiverDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.mailReceiver.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
