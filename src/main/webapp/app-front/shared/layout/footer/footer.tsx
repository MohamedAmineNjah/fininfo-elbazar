import './footer.scss';
import { IRootState } from '../../reducers';
import { connect } from 'react-redux';
import React, { useEffect } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';
import { getSearchEntities, getEntities } from '../../reducers/livraison2.reducer';


import { Card, CardContent, Typography, makeStyles, createStyles, Theme, Grid, fade, colors, Paper } from '@material-ui/core';

import PlaceIcon from '@material-ui/icons/Place';
import MarkunreadIcon from '@material-ui/icons/Markunread';
import { spacing } from '@material-ui/system';
import FacebookIcon from '@material-ui/icons/Facebook';
import YouTubeIcon from '@material-ui/icons/YouTube';
import ArrowForwardIosIcon from '@material-ui/icons/ArrowForwardIos';
import PermPhoneMsgIcon from '@material-ui/icons/PermPhoneMsg';
import TwitterIcon from '@material-ui/icons/Twitter';
import { getinfosociete } from '../../reducers/home.reducer';
import CGUModal from '../../../modules/CGU/CGU_modal';
import { CGUModalFidelite } from '../../../modules/CGU/CGU_modalfidelite';

export interface IFooterProp extends StateProps, DispatchProps { }
export const Footer = (props: IFooterProp) => {
  const [showModal, setShowModal] = React.useState(false);
  const [showModalFid, setShowModalFid] = React.useState(false);
  const { listInformationsociete } = props;
  const handleClose = () => {
    setShowModal(false);
  };
  const handleCloseFid = () => {
    setShowModalFid(false);
  };
  const HandleShow = () => {
    setShowModal(true);
  };
  const HandleShowFid = () => {
    setShowModalFid(true);
  };
  useEffect(() => {
    props.getinfosociete();
  }, []);
  useEffect(() => { }, [props.listInformationsociete]);

  return (
    <div className="footer page-content">
      <Grid container justify="center" className="footerContent">
        <Grid item container xs={10}>
          <Grid container item sm={4} direction="row" spacing={1} style={{ marginBottom: '1rem' }}>
            <Grid item container xs={12}>
              <Grid>
                <Typography variant="h6" style={{ fontSize: '1rem' }}>
                  A propos de nous
                </Typography>
              </Grid>
            </Grid>
            <Grid item container xs={12}>
              <Grid>
                <ArrowForwardIosIcon style={{ fontSize: '1.3rem' }} />
              </Grid>
              <Grid>
                {' '}
                <a onClick={HandleShow}>Vos droits et nos garantis</a>
              </Grid>
            </Grid>
            <Grid item container xs={12}>
              <Grid>
                <ArrowForwardIosIcon style={{ fontSize: '1.3rem' }} />
              </Grid>
              <Grid>
                {' '}
                <a onClick={HandleShowFid}>Avantage de Fidélité</a>
              </Grid>
            </Grid>
            <Grid item container xs={12}>
              <Grid>
                <ArrowForwardIosIcon style={{ fontSize: '1.3rem' }} />
              </Grid>
              <Grid>Livraison express</Grid>
            </Grid>
          </Grid>

          <Grid container item sm={4} direction="row" spacing={1} style={{ marginBottom: '1rem' }}>
            {props.listInformationsociete && props.listInformationsociete[0] ? (
              <div>
                <Grid item container xs={12}>
                  <Grid>
                    <Typography variant="h6" style={{ fontSize: '1rem' }}>
                      Contactez-Nous
                    </Typography>
                  </Grid>
                </Grid>
                <Grid item container xs={12}>
                  <Grid>
                    <MarkunreadIcon />
                  </Grid>
                  <Grid style={{ marginLeft: '.5rem' }}>{props.listInformationsociete[0].email1}</Grid>
                </Grid>
                <Grid item container xs={12}>
                  <Grid>
                    <PlaceIcon />
                  </Grid>

                  <Grid style={{ marginLeft: '.5rem' }}>{props.listInformationsociete[0].adresse}</Grid>
                </Grid>
                <Grid item container xs={12}>
                  <Grid>
                    <PermPhoneMsgIcon />
                  </Grid>

                  <Grid style={{ marginLeft: '.5rem' }}>
                    {props.listInformationsociete[0].tel1}
                    <i>/</i> {props.listInformationsociete[0].tel2}
                  </Grid>
                </Grid>
              </div>
            ) : null}
          </Grid>

          {props.listInformationsociete && props.listInformationsociete[0] ? (
            <Grid container item sm={4} xs={12} direction="row" spacing={1} style={{ marginBottom: '1rem' }}>
              <Grid item container xs={12}>
                <Grid>
                  <Typography variant="h6" style={{ fontSize: '1rem' }}>
                    Suivez-nous
                  </Typography>
                </Grid>
              </Grid>
              <Grid item container xs={12}>
                <Grid>
                  <FacebookIcon />
                </Grid>
                <Grid style={{ marginLeft: '.5rem' }}> {props.listInformationsociete[0].facebook}</Grid>
              </Grid>
              <Grid item container xs={12}>
                <Grid>
                  <YouTubeIcon />
                </Grid>
                <Grid style={{ marginLeft: '.5rem' }}>{props.listInformationsociete[0].youtube}</Grid>
              </Grid>
              <Grid item container xs={12}>
                <Grid>
                  <TwitterIcon />
                </Grid>
                <Grid style={{ marginLeft: '.5rem' }}>{props.listInformationsociete[0].twitter}</Grid>
              </Grid>
            </Grid>
          ) : null}
        </Grid>
      </Grid>
      <CGUModal showModal={showModal} handleClose={handleClose} />
      <CGUModalFidelite listLivrais={props.listLivraison2} getEntities={props.getEntities} match={props.match} showModalFid={showModalFid} handleCloseFid={handleCloseFid} />
    </div>
  );
};
const mapStateToProps = ({ producthome, livraison2 }: IRootState) => ({
  listInformationsociete: producthome.listInfosociete,
  listLivraison2: livraison2.entities,
  match: livraison2.loading,
});

const mapDispatchToProps = {
  getinfosociete,
  getEntities
};
type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;
export default connect(mapStateToProps, mapDispatchToProps)(Footer);
