import './footer.scss';

import React, { useEffect } from 'react';
import { Grid } from '@material-ui/core';
import { IRootState } from 'app/shared/reducers';
import { connect } from 'react-redux';
import { getEntities } from '../../../entities/info-societe/info-societe.reducer';

export const FooterBonLivraison = props => {
  useEffect(() => {
    props.getinfo();
  }, []);
  const { infosocieteEntity } = props;
  return (
    <Grid
      direction="column"
      container
      alignItems="center"
      spacing={3}
      style={{ borderTop: '#efefef', color: '#555', textAlign: 'justify', padding: '1rem' }}
    >
      <Grid item style={{ textAlign: 'justify' }}>
        <div id="footerBon" style={{ display: 'none' }}>
          <Grid item style={{ textAlign: 'center' }}>
            <b>{infosocieteEntity[0] ? 'Matricule Fiscale' : ''}</b> &nbsp;
            {infosocieteEntity[0] ? infosocieteEntity[0].matriculeFiscal : ''}
          </Grid>
          <Grid item style={{ textAlign: 'center' }}>
            {' '}
            <b>{infosocieteEntity[0] ? 'Adresse :' : ''}</b> &nbsp;
            {infosocieteEntity[0] ? infosocieteEntity[0].adresse : ''}&nbsp;
          </Grid>
          <Grid item style={{ textAlign: 'center' }}>
            <b>{infosocieteEntity[0] ? 'Email :' : ''}</b> &nbsp;
            {infosocieteEntity[0] ? infosocieteEntity[0].email1 : ''}&nbsp;
            <b>{infosocieteEntity[0] ? 'Telephone :' : ''}</b> &nbsp;
            {infosocieteEntity[0] ? infosocieteEntity[0].tel1 : ''}
          </Grid>
        </div>
      </Grid>
      <div id="footer">
        <Grid spacing={3} style={{ margin: '0 auto', textAlign: 'center' }}>
          Empowered By
        </Grid>
        <Grid item style={{ margin: '0 auto' }} spacing={3}>
          {' '}
          <img src="content/images/log.png" width="250px" />
        </Grid>
      </div>
    </Grid>
  );
};
