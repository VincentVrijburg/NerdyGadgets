package com.nerdygadgets.monitoring.data.utils;

import com.nerdygadgets.monitoring.data.entities.*;
import com.nerdygadgets.monitoring.data.models.components.ComponentGet;
import com.nerdygadgets.monitoring.data.models.components.details.ComponentDetailGet;
import com.nerdygadgets.monitoring.data.models.designs.DesignGet;
import com.nerdygadgets.monitoring.data.models.logs.LogGet;
import com.nerdygadgets.monitoring.data.models.users.UserGet;
import org.jasypt.util.password.StrongPasswordEncryptor;
import oshi.SystemInfo;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class holds static classes with the purpose of quickly manipulating using methods.
 */
public class Utils {

    /**
     * This static class holds methods to format Date objects.
     */
    public static class DateUtils {

        /**
         * Format a Date object to the standard date format string.
         * @param date A Date object to format.
         * @return A string in the following format: 'yyyy-MM-dd HH:mm:ss'.
         */
        public static String formatDate(Date date) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.format(date);
        }
    }

    /**
     * This static class holds methods to format Entity objects.
     */
    public static class EntityUtils {
        public static UserGet formatUserGet(User user) {
            if (user == null)
                return null;

            UserGet userGet = new UserGet();
            userGet.id = user.getId();
            userGet.username = user.getUsername();
            userGet.password = user.getPassword();
            userGet.isActive = user.isActive();
            userGet.updated = user.getUpdated();
            userGet.created = user.getCreated();

            return userGet;
        }

        public static DesignGet formatDesignGet(Design design) {
            if (design == null)
                return null;

            DesignGet designGet = new DesignGet();
            designGet.id = design.getId();
            designGet.user = formatUserGet(design.getUser());

            designGet.components = new ArrayList<>();
            for (DesignComponent designComponent: design.getDesignComponents()) {
                Component component = designComponent.getComponent();
                designGet.components.add(formatComponentGet(component));
            }

            return designGet;
        }

        public static LogGet formatLogGet(Log log) {
            if (log == null)
                return null;

            LogGet logGet = new LogGet();

            logGet.id = log.getId();
            logGet.type = log.getType().toString();
            logGet.error = log.getError();
            logGet.isResolved = log.isResolved();
            logGet.updated = log.getUpdated();
            logGet.created = log.getCreated();

            return logGet;
        }

        public static ComponentGet formatComponentGet(Component component) {
            if (component == null)
                return null;

            ComponentGet componentGet = new ComponentGet();
            componentGet.id = component.getId();
            componentGet.name = component.getName().toString();
            componentGet.type = component.getType().toString();
            componentGet.availability = component.getAvailability();
            componentGet.cost = component.getCost();
            componentGet.updated = component.getUpdated();
            componentGet.created = component.getCreated();

            return componentGet;
        }

        public static ComponentDetailGet formatComponentDetailGet(ComponentDetail componentDetail) {
            if (componentDetail == null)
                return null;

            ComponentDetailGet componentDetailGet = new ComponentDetailGet();

            componentDetailGet.id = componentDetail.getId();
            componentDetailGet.name = componentDetail.getName();
            componentDetailGet.ipAddress = componentDetail.getIpAddress();
            componentDetailGet.availableSince = componentDetail.getAvailableSince();
            componentDetailGet.processorLoad = componentDetail.getProcessorLoad();
            componentDetailGet.memoryUsed = componentDetail.getMemoryUsed();
            componentDetailGet.memoryTotal = componentDetail.getMemoryTotal();
            componentDetailGet.diskspaceUsed = componentDetail.getDiskspaceUsed();
            componentDetailGet.diskspaceTotal = componentDetail.getDiskspaceTotal();
            componentDetailGet.updated = componentDetail.getUpdated();
            componentDetailGet.created = componentDetail.getCreated();

            return componentDetailGet;
        }
    }

    /**
     * This static class holds methods to encrypt and verify passwords.
     */
    public static class PasswordUtils {
        public static String encryptPassword(String password) {
            try {
                // Create MessageDigest instance for MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                //Add password bytes to digest
                md.update(password.getBytes());
                //Get the hash's bytes
                byte[] bytes = md.digest();
                //This bytes[] has bytes in decimal format;
                //Convert it to hexadecimal format
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< bytes.length ;i++)
                {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                //Get complete hashed password in hex format
                return sb.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        public static boolean verifyPassword(String plainPassword, String encryptedPassword) {
            String passwordToCompare = encryptPassword(plainPassword);
            return passwordToCompare.equals(encryptedPassword);
        }
    }

    public static class MonitoringUtils {

        public static Date getAvailableSince() {
            long uptime = new SystemInfo().getOperatingSystem().getSystemUptime();
            Date availableSince = new Date();
            availableSince.setTime(availableSince.getTime() - (uptime * 1000));

            return availableSince;
        }

        public static double getSystemCpuLoad() throws Exception {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList list = mbs.getAttributes(name, new String[]{ "SystemCpuLoad" });

            if (list.isEmpty())
                return Double.NaN;

            Attribute att = (Attribute)list.get(0);
            Double value  = (Double)att.getValue();

            // Usually takes a couple of seconds before we get real values
            if (value == -1.0)
                return Double.NaN;

            // Returns a percentage value with 1 decimal point precision
            return ((int)(value * 1000) / 10.0);
        }

        public static double getTotalMemory() {
            long totalMemory = new SystemInfo().getHardware().getMemory().getTotal();
            int mb = 1024 * 1024;

            return totalMemory / mb;
        }

        public static double getUsedMemory() {
            long totalMemory = new SystemInfo().getHardware().getMemory().getTotal();
            long availableMemory = new SystemInfo().getHardware().getMemory().getAvailable();
            int mb = 1024 * 1024;

            return (totalMemory - availableMemory) / mb;
        }

        public static double getUsedDiskspace() throws Exception {
            double usedDiskspace = 0;

            FileSystem fileSystem = FileSystems.getDefault();
            Iterable<FileStore> fileStores = fileSystem.getFileStores();
            for (FileStore s: fileStores) {
                usedDiskspace += (s.getTotalSpace() / 1073741824f) - (s.getUsableSpace() / 1073741824f);
            }

            return usedDiskspace;
        }

        public static double getTotalDiskspace() throws Exception {
            double totalDiskspace = 0;

            FileSystem fileSystem = FileSystems.getDefault();
            Iterable<FileStore> fileStores = fileSystem.getFileStores();
            for (FileStore s: fileStores) {
                totalDiskspace += s.getTotalSpace() / 1073741824f;
            }

            return totalDiskspace;
        }
    }
}
