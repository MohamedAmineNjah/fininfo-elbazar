import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Slides from './slides';
import SlidesDetail from './slides-detail';
import SlidesUpdate from './slides-update';
import SlidesDeleteDialog from './slides-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SlidesDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SlidesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SlidesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SlidesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Slides} />
    </Switch>
  </>
);

export default Routes;
