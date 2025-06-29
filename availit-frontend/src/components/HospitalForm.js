import React, { useState, useEffect } from 'react';
import { createHospital, updateHospital } from '../services/hospitalService';
import {
  TextField, Button, Checkbox, FormControlLabel, Typography, Paper, Grid, Stack, Snackbar, Alert
} from '@mui/material';

const initialState = {
  hospitalName: '',
  totalBeds: 0,
  availableBeds: 0,
  oxygenAvailable: false,
  address: '',
  contactNumber: '',
  icuBeds: 0,
  ventilators: 0,
  locations: []
};

function HospitalForm({ editing, onSuccess, onCancel }) {
  const [form, setForm] = useState(initialState);
  const [errors, setErrors] = useState({});
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    if (editing) setForm(editing);
    else setForm(initialState);
    setErrors({});
  }, [editing]);

  const validate = () => {
    const newErrors = {};
    if (!form.hospitalName) newErrors.hospitalName = 'Hospital name is required';
    if (!form.address) newErrors.address = 'Address is required';
    if (!form.contactNumber) newErrors.contactNumber = 'Contact number is required';
    if (form.totalBeds < 0) newErrors.totalBeds = 'Total beds cannot be negative';
    if (form.availableBeds < 0) newErrors.availableBeds = 'Available beds cannot be negative';
    if (form.icuBeds < 0) newErrors.icuBeds = 'ICU beds cannot be negative';
    if (form.ventilators < 0) newErrors.ventilators = 'Ventilators cannot be negative';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = e => {
    const { name, value, type, checked } = e.target;
    setForm(f => ({
      ...f,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleLocationsChange = e => {
    try {
      setForm(f => ({ ...f, locations: JSON.parse(e.target.value) }));
    } catch {
      setForm(f => ({ ...f, locations: [] }));
    }
  };

  const handleSubmit = async e => {
    e.preventDefault();
    if (!validate()) return;
    setSubmitting(true);
    try {
      const action = editing ? updateHospital(editing.id, form) : createHospital(form);
      await action;
      setSnackbar({ open: true, message: editing ? 'Hospital updated!' : 'Hospital created!', severity: 'success' });
      onSuccess();
      setForm(initialState);
    } catch (err) {
      setSnackbar({ open: true, message: 'Error: ' + (err?.response?.data?.message || 'Failed to save hospital'), severity: 'error' });
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Paper elevation={2} sx={{ p: 2, mb: 2 }}>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={4}>
            <TextField name="hospitalName" label="Hospital Name" value={form.hospitalName} onChange={handleChange} required fullWidth error={!!errors.hospitalName} helperText={errors.hospitalName} />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField name="totalBeds" label="Total Beds" type="number" value={form.totalBeds} onChange={handleChange} required fullWidth error={!!errors.totalBeds} helperText={errors.totalBeds} />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField name="availableBeds" label="Available Beds" type="number" value={form.availableBeds} onChange={handleChange} required fullWidth error={!!errors.availableBeds} helperText={errors.availableBeds} />
          </Grid>
          <Grid item xs={12} sm={4}>
            <FormControlLabel
              control={
                <Checkbox name="oxygenAvailable" checked={form.oxygenAvailable} onChange={handleChange} />
              }
              label="Oxygen Available"
            />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField name="address" label="Address" value={form.address} onChange={handleChange} required fullWidth error={!!errors.address} helperText={errors.address} />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField name="contactNumber" label="Contact Number" value={form.contactNumber} onChange={handleChange} required fullWidth error={!!errors.contactNumber} helperText={errors.contactNumber} />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField name="icuBeds" label="ICU Beds" type="number" value={form.icuBeds} onChange={handleChange} required fullWidth error={!!errors.icuBeds} helperText={errors.icuBeds} />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField name="ventilators" label="Ventilators" type="number" value={form.ventilators} onChange={handleChange} required fullWidth error={!!errors.ventilators} helperText={errors.ventilators} />
          </Grid>
          <Grid item xs={12}>
            <TextField
              name="locations"
              label='Locations (JSON)'
              value={JSON.stringify(form.locations)}
              onChange={handleLocationsChange}
              placeholder='[{"address":"...","city":"...","state":"...","zipCode":"..."}]'
              fullWidth
            />
          </Grid>
        </Grid>
        <Stack direction="row" spacing={2} sx={{ mt: 2 }}>
          <Button type="submit" variant="contained" color="secondary" size="large" disabled={submitting}>
            {editing ? 'Update' : 'Create'}
          </Button>
          {editing && (
            <Button type="button" variant="outlined" color="secondary" onClick={onCancel} size="large" disabled={submitting}>
              Cancel
            </Button>
          )}
        </Stack>
      </form>
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
    </Paper>
  );
}

export default HospitalForm;