package com.iot.admin.admin.service;

import java.util.ArrayList;
import java.util.List;

import com.iot.admin.admin.dto.VirtualDeviceDetails;
import com.iot.admin.admin.dto.VirtualDeviceForm;
import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.VirtualDevice;
import com.iot.admin.admin.repository.DeviceRepository;
import com.iot.admin.admin.repository.VirtualDeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class VirtualDeviceServiceImpl implements VirtualDeviceService{

    @Autowired
    private VirtualDeviceRepository virtualDeviceRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<VirtualDeviceDetails> findAll() {
        Iterable<VirtualDevice> list_devices = virtualDeviceRepository.findAll();
        List<VirtualDeviceDetails> list_virtual_details = new ArrayList<>();

        list_devices.forEach(virtualDevice -> {
            VirtualDeviceDetails details = new VirtualDeviceDetails();
            details.setEntity(virtualDevice);
            list_virtual_details.add(details);
        });

        return list_virtual_details;
    }

    @Override
    public VirtualDeviceDetails create(VirtualDeviceForm formData) {
        VirtualDevice virtualDevice = formData.getEntity();
        virtualDevice.setTag(generateTag(virtualDevice.getDeviceReference().getId(), virtualDevice.getIsGateway()));
        VirtualDeviceDetails virtualDeviceDetails = new VirtualDeviceDetails();
        virtualDeviceDetails.setEntity(virtualDeviceRepository.save(virtualDevice));
        return virtualDeviceDetails;
    }

    @Override
    public VirtualDeviceDetails update(VirtualDeviceForm formData, Long id) {        
        VirtualDevice virtualDevice= virtualDeviceRepository.getById(id);
        VirtualDeviceDetails virtualDeviceDetails = new VirtualDeviceDetails();
        formData.setEntity(virtualDevice);
        virtualDeviceDetails.setEntity(virtualDeviceRepository.save(virtualDevice));
        return virtualDeviceDetails;
    }

    @Transactional
    @Override
    public Boolean deleteById(Long id){
        if(validateExist(id)){
            virtualDeviceRepository.deleteById(id);
            return true;
        }
        return false;
    }




    //Metodo para generar TAGs para dispositivos.
    private String generateTag(Long deviceReferenceId,Boolean isGateway){

        Device device = deviceRepository.getById(deviceReferenceId);

        String tagInit = "VD";

        if(isGateway){
            tagInit = "VDG";
        }
        
        String totalDevices = Long.toString(virtualDeviceRepository.findByDeviceReference(device).size()+1);
        String tag = tagInit + device.getName().substring(0,1).toUpperCase() +  totalDevices;
        return tag;
    }


    // Method for validate if propertie is already exists
    private boolean validateExist(Long id) {
        if (!virtualDeviceRepository.existsById(id)) {
            return false;
        } return true;
    } 
    
}
