# EasyTutoFileManager

Simple File Manager App 

* Request Storage Permission
* List Files and Folders
* Open Files on Click
* Delete,Move,Rename Options

![Capture](https://user-images.githubusercontent.com/68380115/135757293-e539dcb0-6475-44d5-be2a-4d14dd7c6ef8.PNG)

StorageDirectory
```java
     String path = Environment.getExternalStorageDirectory().getPath();
     FileManager.jump(MainActivity.this,path);

```

app private directory
```java
 FileManager.jump(MainActivity.this);
```