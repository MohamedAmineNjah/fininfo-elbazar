import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InfoSociete from './info-societe';
import InfoSocieteDetail from './info-societe-detail';
import InfoSocieteUpdate from './info-societe-update';
import InfoSocieteDeleteDialog from './info-societe-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InfoSocieteDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InfoSocieteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InfoSocieteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InfoSocieteDetail} />
      <ErrorBoundaryRoute path={match.url} component={InfoSociete} />
    </Switch>
  </>
);

export default Routes;
