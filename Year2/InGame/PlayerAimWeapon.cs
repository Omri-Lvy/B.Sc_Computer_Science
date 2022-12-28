using UnityEngine;


public class PlayerAimWeapon : MonoBehaviour
{

    private Transform aimTransform;

    // Start is called before the first frame update
    void Start()
    {
        aimTransform = transform.Find("Aim");
    }

    // Update is called once per frame
    void Update()
    {
        Vector3 mousePos = GetMousePosition();
        Vector3 aimDirection = (mousePos - transform.position).normalized;
        float angle = Mathf.Atan2(aimDirection.y, aimDirection.x) * Mathf.Deg2Rad;
        aimTransform.eulerAngles = new Vector3(0, 0, angle);
        Debug.Log(angle);
    }
    
    private static Vector3 GetMousePosition()
    {
        Vector3 vector = GetMousePosition();
        vector.z = 0f;
        return vector;
    }
}

