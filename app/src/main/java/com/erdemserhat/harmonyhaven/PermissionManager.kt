import android.content.Context;
import android.content.pm.PackageManager;


object PermissionManager {
    // İzin kontrolü
    fun checkPermission(context: Context, permission: String?): Boolean {
        val result = context.checkCallingOrSelfPermission(permission!!)
        return result == PackageManager.PERMISSION_GRANTED
    }
}
