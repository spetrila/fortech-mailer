import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CampaignComponent } from './campaign.component';
import { CampaignDetailComponent } from './campaign-detail.component';
import { CampaignPopupComponent } from './campaign-dialog.component';
import { CampaignDeletePopupComponent } from './campaign-delete-dialog.component';

import { Principal } from '../../shared';

export const campaignRoute: Routes = [
  {
    path: 'campaign',
    component: CampaignComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.campaign.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'campaign/:id',
    component: CampaignDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.campaign.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const campaignPopupRoute: Routes = [
  {
    path: 'campaign-new',
    component: CampaignPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.campaign.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'campaign/:id/edit',
    component: CampaignPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.campaign.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'campaign/:id/delete',
    component: CampaignDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fortechMailerApp.campaign.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
