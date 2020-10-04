import React from 'react';
import { Typography, Box } from '@material-ui/core';

export interface IUserSettingsProps {
  [x: string]: any;
  children?: React.ReactNode;
  dir?: string;
  index: any;
  value: any;
}

function TabPanel(props: IUserSettingsProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`full-width-tabpanel-${index}`}
      aria-labelledby={`full-width-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={7}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

export default TabPanel;
