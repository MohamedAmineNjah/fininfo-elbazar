import { makeStyles, Theme } from '@material-ui/core/styles';

export function a11yProps(index: any) {
  return {
    id: `full-width-tab-${index}`,
    'aria-controls': `full-width-tabpanel-${index}`,
  };
}

export const useStyles = makeStyles((theme: Theme) => ({
  root: {
    flexGrow: 1,
  },
}));

export const handleTabClick = key => {
  this.props.history.push(`/${key}`);
};
