import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AffectationZone from './affectation-zone';
import AffectationZoneDetail from './affectation-zone-detail';
import AffectationZoneUpdate from './affectation-zone-update';
import AffectationZoneDeleteDialog from './affectation-zone-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AffectationZoneDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AffectationZoneUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AffectationZoneUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AffectationZoneDetail} />
      <ErrorBoundaryRoute path={match.url} component={AffectationZone} />
    </Switch>
  </>
);

export default Routes;
