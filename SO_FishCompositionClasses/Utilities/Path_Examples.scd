// 日 13  4 2025 18:53
// testing how to work with SavePath, using
// PathTest and Path
//: 1. Pathtest (preliminary)
SavePath(PathTest);
SavePath(PathTest());
//: 2. Path
Path(\default);
//: 3. PathBuffer
a = PathBuffer("pathbufferexample1");
a.buffer;
//:
a.path;
b = Buffer.read(Server.default, a.path);
//:
b;