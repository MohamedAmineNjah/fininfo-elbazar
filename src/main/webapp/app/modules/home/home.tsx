import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
    <Col xs="6" >
      <Row sm="12" >
        <Col style={{'margin-top':'20%'}} md={{ size: 12, offset: 3 }} >
        <Col style={{'color':'#777','padding':'10px'}} >
          <h4>Livraison porte à porte</h4>
          </Col>
          <Col style={{'color':'#777','padding':'10px'}} >
          <h6>El Bazar vous livre jusqu&apos;à chez vous toutes vos commandes</h6>
          </Col>
        </Col>
      </Row>
      
      </Col>
      <Col xs="6" >
      <span className="hipster rounded" />
      </Col>
  </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
