mkdir auh.png, rak.png, dxb.png, shj.png
mkdir auh.avif, rak.avif, dxb.avif, shj.avif
mkdir auh.webp, rak.webp, dxb.webp, shj.webp

./pdftopng.exe -r 300 -alpha rak.pdf rak.png/t
./pdftopng.exe -r 300 -alpha auh.pdf auh.png/t
./pdftopng.exe -r 300 -alpha dxb.pdf dxb.png/t
./pdftopng.exe -r 300 -alpha shj.pdf shj.png/t
#./mutool.exe draw -r 300 -F png -o "./rak.png/%3d.png" rak.pdf

<# Not needed
# Remove prefix 't-' from file names
get-childitem rak.png/*.png | foreach { rename-item $_ $_.Name.Replace("t-", "") }
# Remove leading zeros from file names
Get-ChildItem . -File -Filter "rak.png/0*.*" | Rename-Item -NewName { $_.Name.TrimStart('0') }
#>

Get-ChildItem -Path auh.png/* -Recurse -Include @(Get-Content auh.txt) | Remove-Item -Verbose
Get-ChildItem -Path rak.png/* -Recurse -Include @(Get-Content rak.txt) | Remove-Item -Verbose
Get-ChildItem -Path dxb.png/* -Recurse -Include @(Get-Content dxb.txt) | Remove-Item -Verbose
Get-ChildItem -Path shj.png/* -Recurse -Include @(Get-Content shj.txt) | Remove-Item -Verbose

$i = 0
Get-ChildItem rak.png/* | %{Rename-Item $_ -NewName ('{0:d}.png' -f $i++)}
$i = 0
Get-ChildItem dxb.png/* | %{Rename-Item $_ -NewName ('{0:d}.png' -f $i++)}
$i = 0
Get-ChildItem auh.png/* | %{Rename-Item $_ -NewName ('{0:d}.png' -f $i++)}
$i = 0
Get-ChildItem shj.png/* | %{Rename-Item $_ -NewName ('{0:d}.png' -f $i++)}

# avifenc -> https://jeremylee.sh/bins/
# Powershell 7, support parallel processing -> https://github.com/PowerShell/PowerShell/releases
# cwebp -> https://storage.googleapis.com/downloads.webmproject.org/releases/webp/index.html
# heif-enc -> https://github.com/pphh77/libheif-Windowsbinary/releases
(Get-ChildItem -Path rak.png -Recurse -ErrorAction SilentlyContinue -Force) | Foreach-Object -Parallel {
	$basename = $_.BaseName
	#./ffmpeg -i ./rak.png/$basename.png -c:v libaom-av1 -still-picture 1 rak.avif/$basename.avif
	#./avifenc.exe -j all ./rak.png/$basename.png rak.avif/$basename.avif
	#./heif-enc.exe ./rak.png/$basename.png -o rak.heif/$basename.heif
	./cwebp.exe -q 50 ./rak.png/$basename.png -o rak.webp/$basename.webp
}

(Get-ChildItem -Path dxb.png -Recurse -ErrorAction SilentlyContinue -Force) | Foreach-Object -Parallel {
	$basename = $_.BaseName
	./cwebp.exe -q 50 ./dxb.png/$basename.png -o dxb.webp/$basename.webp
}

(Get-ChildItem -Path auh.png -Recurse -ErrorAction SilentlyContinue -Force) | Foreach-Object -Parallel {
	$basename = $_.BaseName
	./cwebp.exe -q 50 ./auh.png/$basename.png -o auh.webp/$basename.webp
}

(Get-ChildItem -Path shj.png -Recurse -ErrorAction SilentlyContinue -Force) | Foreach-Object -Parallel {
	$basename = $_.BaseName
	./cwebp.exe -q 50 ./shj.png/$basename.png -o shj.webp/$basename.webp
}

<#
Foreach ($file in Get-ChildItem -Path rak.png -Recurse -ErrorAction SilentlyContinue -Force) {
	$basename = $file.BaseName
	./avifenc.exe -j all ./rak.png/$file rak.avif/$basename.avif
}
#>
