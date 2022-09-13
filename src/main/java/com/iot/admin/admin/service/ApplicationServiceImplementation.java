package com.iot.admin.admin.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.admin.admin.dto.ApplicationDetail;
import com.iot.admin.admin.dto.ApplicationForm;
import com.iot.admin.admin.dto.DeviceApplicationForm;
import com.iot.admin.admin.entity.Application;
import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.repository.ApplicationRepository;
import com.iot.admin.admin.repository.DeviceRepository;
import com.iot.admin.admin.utils.RestClient;

@Service
public class ApplicationServiceImplementation implements ApplicationService{

    public String toJsonProperty(Object prop){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(prop);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
        
    }

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public ApplicationDetail create(ApplicationForm formData) {
        // Validates device fields.
        Application app = formData.getEntity();
        ApplicationDetail app_detail = new ApplicationDetail();
        List<Application> apps = repository.findAll();
        Long new_id;
        if(apps.size()>0){
            Application last_app = apps.get(apps.size()-1);
            new_id = last_app.getId()+1L;
        } else {
            new_id = 1L;
        }
        app.setGlobal_id(new_id);
        app_detail.setEntity(repository.save(app));
        return app_detail;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean addDevice(DeviceApplicationForm formData,Long app_id) {
        // Validates device fields.
        Application app = repository.getById(app_id);
        Long device_id = formData.getDevice_id();
        Device device = deviceRepository.getById(device_id);
        Device deviceParent = device.getDeviceParent();
        //Synchronize Device
        if(deviceParent!=null){
            if(app.getDevices().size()==0){
                for(Property prop:deviceParent.getProperties()){
                    if(prop.getName().equals("gateway_ipv4")){
                        String ipv4 = prop.getValue();
                        String uri = "http://"+ipv4+":5000/apps/create/api/";
                        ApplicationDetail app_detail = new ApplicationDetail();
                        app_detail.setEntity(app);
                        String app_json = toJsonProperty(app_detail);
                        RestClient client = new RestClient();
                        client.post(uri, app_json);                        
                        String uri2 = "http://"+ipv4+":5000/apps/device/api/global/"+app.getGlobal_id().toString()+"/";
                        String d_json = toJsonProperty(formData);
                        client.post(uri2, d_json);
                    }            
                }
            } else{
                boolean exist = false;
                //Verificar si ya existe la app en el dispositivo
                for(Device dev_app:app.getDevices()){
                    if(dev_app.getDeviceParent().getGlobal_id()==device.getDeviceParent().getGlobal_id()){
                        exist = true;
                    }
                }
                if(!exist){
                    for(Property prop:deviceParent.getProperties()){
                        if(prop.getName().equals("gateway_ipv4")){
                            String ipv4 = prop.getValue();
                            String uri = "http://"+ipv4+":5000/apps/create/api/";
                            ApplicationDetail app_detail = new ApplicationDetail();
                            app_detail.setEntity(app);
                            String app_json = toJsonProperty(app_detail);
                            RestClient client = new RestClient();
                            client.post(uri, app_json);                        
                            String uri2 = "http://"+ipv4+":5000/apps/device/api/global/"+app.getGlobal_id().toString()+"/";
                            String d_json = toJsonProperty(formData);
                            client.post(uri2, d_json);
                        }            
                    }
                } else {
                    for(Property prop:deviceParent.getProperties()){
                        if(prop.getName().equals("gateway_ipv4")){
                            String ipv4 = prop.getValue();
                            RestClient client = new RestClient();                      
                            String uri2 = "http://"+ipv4+":5000/apps/device/api/global/"+app.getGlobal_id().toString()+"/";
                            String d_json = toJsonProperty(formData);
                            client.post(uri2, d_json);
                        }            
                    }
                }


            }            
        }        
        app.getDevices().add(device);
        repository.save(app);
        return true;
    }

    @Override
    public boolean deleteDevice(DeviceApplicationForm formData,Long app_id) {
        // Validates device fields.
        Application app = repository.getById(app_id);
        Long device_id = formData.getDevice_id();
        Device device = deviceRepository.getById(device_id);
        Device deviceParent = device.getDeviceParent();
        //Synchronize Device
        if(deviceParent!=null){
            System.out.println(app.getDevices().size());
            if(app.getDevices().size()==1){
                for(Property prop:deviceParent.getProperties()){
                    if(prop.getName().equals("gateway_ipv4")){
                        String ipv4 = prop.getValue();
                        String uri = "http://"+ipv4+":5000/apps/device/delete/api/global/"+app.getGlobal_id().toString()+"/";
                        String d_json = toJsonProperty(formData);
                        RestClient client = new RestClient();
                        client.post(uri, d_json); 
                        String uri2 = "http://"+ipv4+":5000/apps/delete/api/global/"+app.getGlobal_id().toString()+"/";
                        client.delete(uri2);
                        break;                    
                    }            
                }
            } else if(app.getDevices().size()>1){
                for(Property prop:deviceParent.getProperties()){
                    if(prop.getName().equals("gateway_ipv4")){
                        String ipv4 = prop.getValue();
                        String uri = "http://"+ipv4+":5000/apps/device/delete/api/global/"+app.getGlobal_id().toString()+"/";
                        String d_json = toJsonProperty(formData);
                        RestClient client = new RestClient();
                        client.post(uri, d_json);
                        boolean eliminar = true;
                        for(Device dev_app:app.getDevices()){
                            if(dev_app.getDeviceParent().getGlobal_id()==device.getDeviceParent().getGlobal_id() && dev_app.getGlobal_id() != device.getGlobal_id()){
                                eliminar = false;
                            }
                        }
                        if(eliminar){
                            String uri2 = "http://"+ipv4+":5000/apps/delete/api/global/"+app.getGlobal_id().toString()+"/";
                            client.delete(uri2);
                        }                       
                        break;                     
                    }            
                }
            }  
        }        
        app.getDevices().remove(device);
        repository.save(app);
        return true;
    }

    
    
}
