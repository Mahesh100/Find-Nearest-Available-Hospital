import React, { useEffect, useState } from 'react';
import { getAllHospitals, deleteHospital } from '../services/hospitalService';
import {
  TextField, Stack, Typography, CircularProgress, Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button, Snackbar, Alert, Grid, Card, CardHeader, CardContent, Avatar, IconButton, Divider
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import LocalHospitalIcon from '@mui/icons-material/LocalHospital';

function HospitalList({ onEdit, refresh }) {
  const [hospitals, setHospitals] = useState([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(true);
  const [deleteId, setDeleteId] = useState(null);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  const fetchHospitals = () => {
    setLoading(true);
    getAllHospitals({ page: 0, size: 100, sort: 'hospitalName,asc' })
      .then(res => {
        setHospitals(res.data.content);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchHospitals();
    // eslint-disable-next-line
  }, [refresh]);

  const filtered = hospitals.filter(h =>
    h.hospitalName.toLowerCase().includes(search.toLowerCase())
  );

  const handleDelete = async () => {
    try {
      await deleteHospital(deleteId);
      setSnackbar({ open: true, message: 'Hospital deleted!', severity: 'success' });
      fetchHospitals();
    } catch (err) {
      setSnackbar({ open: true, message: 'Error deleting hospital', severity: 'error' });
    } finally {
      setDeleteId(null);
    }
  };

  if (loading) return <Stack alignItems="center" sx={{ mt: 4 }}><CircularProgress color="primary" /></Stack>;

  return (
    <div>
      <TextField
        label="Search hospitals"
        variant="outlined"
        fullWidth
        margin="normal"
        value={search}
        onChange={e => setSearch(e.target.value)}
      />
      <Grid container spacing={3}>
        {filtered.map(hospital => (
          <Grid item xs={12} sm={6} md={4} key={hospital.id}>
            <Card sx={{ background: '#e3f2fd', borderRadius: 2, boxShadow: 3 }}>
              <CardHeader
                avatar={
                  <Avatar sx={{ bgcolor: 'primary.main' }}>
                    <LocalHospitalIcon />
                  </Avatar>
                }
                title={<Typography variant="h6" fontWeight="bold">{hospital.hospitalName}</Typography>}
                action={
                  <Stack direction="row" spacing={1}>
                    <IconButton color="secondary" onClick={() => onEdit(hospital)}>
                      <EditIcon />
                    </IconButton>
                    <IconButton color="error" onClick={() => setDeleteId(hospital.id)}>
                      <DeleteIcon />
                    </IconButton>
                  </Stack>
                }
              />
              <Divider />
              <CardContent>
                <Typography variant="body2">
                  Beds: {hospital.availableBeds}/{hospital.totalBeds} | ICU Beds: {hospital.icuBeds} | Ventilators: {hospital.ventilators} | Oxygen: {hospital.oxygenAvailable ? 'Yes' : 'No'}
                  <br />
                  Address: {hospital.address}
                  <br />
                  Contact: {hospital.contactNumber}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
      <Dialog open={!!deleteId} onClose={() => setDeleteId(null)}>
        <DialogTitle>Delete Hospital</DialogTitle>
        <DialogContent>
          <DialogContentText>Are you sure you want to delete this hospital?</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteId(null)} color="primary">Cancel</Button>
          <Button onClick={handleDelete} color="error" variant="contained">Delete</Button>
        </DialogActions>
      </Dialog>
      <Snackbar 
        open={snackbar.open} 
        autoHideDuration={4000} 
        onClose={() => setSnackbar(s => ({ ...s, open: false }))}
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      >
        <Alert 
          onClose={() => setSnackbar(s => ({ ...s, open: false }))} 
          severity={snackbar.severity} 
          sx={{ width: '100%', fontSize: '1.2rem', fontWeight: 'bold', boxShadow: 6 }}
          elevation={6}
          variant="filled"
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </div>
  );
}

export default HospitalList; 