import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';
import { Card, CardContent, Typography, makeStyles, createStyles, Theme, Grid, fade, colors, Paper } from '@material-ui/core';

const Footer = props => (
  <Grid
    container
    justify="center"
    alignItems="center"
    direction="column"
    spacing={3}
    sm={12}
    xs={12}
    style={{ borderTop: '#efefef', color: '#555', padding: '1rem', paddingLeft: '5rem', backgroundColor: '#fff', margin: '0 auto' }}
  >
    <div id="footer">
      <Grid spacing={3} style={{ margin: '0 auto' }}>
        Empowered By
      </Grid>
      <Grid item style={{ margin: '0 auto' }} spacing={3}>
        {' '}
        <img src="content/images/log.png" width="250px" />
      </Grid>
    </div>
  </Grid>
);

export default Footer;
